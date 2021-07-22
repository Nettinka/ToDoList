
const buttonAllCases = document.querySelector('.buttonAllCases');

const container = document.querySelector('.container');

const btnAddCase = document.querySelector('#createcase');

const form = document.querySelector('form');

const deleteCaseBtn = document.querySelectorAll('.delete_btn');

const redSendbutton = document.querySelectorAll('.red_sendbutton');

const bodyPlace = document.querySelector('body');

const $forform = document.querySelector('.forform');

//Получение всех задач
function getPosts(cb){

const xhr = new XMLHttpRequest();
xhr.open('GET', 'http://localhost:8080/cases/');
xhr.addEventListener('load', () => {
    const response = JSON.parse(xhr.responseText);
    cb(response);

}
)
xhr.send();
}


/*buttonAllCases.addEventListener('click', e => {

    container.innerHTML = "";
    getPosts(response => {

        const fragment = document.createDocumentFragment();
            response.forEach(el => {
                const card = document.createElement('div');
                card.classList.add('caseslist');
                const cardBody = document.createElement('div');
                cardBody.classList.add('case_info');
                cardBody.id = el.id;
                const title = document.createElement('p');
                title.classList.add('case_title');
                title.textContent = el.title;
                const description = document.createElement('p');
                description.classList.add('case_description');
                description.textContent = el.description;
                cardBody.appendChild(title);
                cardBody.appendChild(description);
                const button = document.createElement('button');
                button.classList.add('red_sendbutton');
                button.textContent = 'Редактировать задачу';
                cardBody.appendChild(button);
                card.appendChild(cardBody);
                fragment.appendChild(card);
            });
            container.appendChild(fragment);
            })

}) */

//Добавление задачи
const appendCase = function (data) {

const fragment = document.createDocumentFragment();
    const card = document.createElement('div');
    card.classList.add('caseslist');
    const cardBody = document.createElement('div');
    cardBody.classList.add('case_info');
    cardBody.id = data.id;
    const title = document.createElement('p');
    title.classList.add('case_title');
    title.textContent = data.title;
    const description = document.createElement('p');
    description.classList.add('case_description');
    description.textContent = data.description;
    cardBody.appendChild(title);
    cardBody.appendChild(description);
    const button = document.createElement('button');
    button.classList.add('red_sendbutton');
    button.id = `red_${data.id}`;
    button.textContent = 'Редактировать задачу';

    var btnChest = document.createElement('button');
    btnChest.classList.add('delete_btn');
    btnChest.id = `delete_${data.id}`;
    btnChest.textContent = 'Удалить задачу';

    cardBody.appendChild(btnChest);
    cardBody.appendChild(button);
    card.appendChild(cardBody);
    fragment.appendChild(card);
    container.appendChild(fragment);

};


$('#createcase').click(function () {
    var data = $(form).serialize();
    $.ajax({
        method: "POST",
        url: '/cases/',
        data: data,
        success: function (response) {
            var oneCase = {};
            oneCase.id = response;
            var dataArray = $(form).serializeArray();
            for(i in dataArray) {
                oneCase[dataArray[i]['name']] = dataArray[i]['value'];
            }

            console.log(dataArray);
            console.log(oneCase);
            appendCase(oneCase);
            $('[name=title]').val('');
            $('[name=description]').val('');
        }
    });
    return false;
});

//Удаление задачи
deleteCaseBtn.forEach((elem)=>{
  elem.addEventListener('click',function(){
  let revDiv = this.parentElement;
        deleteCase(revDiv);
      revDiv.remove();

  });
});

function deleteCase(el){
      const thisId = el.getAttribute('data-id');
      $.ajax({
          method: "DELETE",
          url: `/cases/${thisId}/`
      });
  }

//Редактирование элемента
redSendbutton.forEach((elem)=>{
  elem.addEventListener('click',function(){
  let revDiv = this.parentElement;
        openRedForm(revDiv, $forform);
        container.style.opacity = 0.1;

        const $sendButton = document.querySelector('#send_button');
        const $canslButton = document.querySelector('#cansel_button');

        $sendButton.addEventListener('click', function () {
            const buttonParent = this.parentElement;
            console.log(buttonParent);
            putCase(buttonParent);
            container.style.opacity = 1;
            $forform.innerHTML = "";
        })


        $canslButton.addEventListener('click', function (){
            container.style.opacity = 1;
            $forform.innerHTML = "";
        })
  });
});

function putCase(elem) {
    const thisId = elem.getAttribute('data-id');
    const titleCase = elem.querySelector('input[name="title"]').value;
    console.log(titleCase);
    const descriptionCase = elem.querySelector('input[name="description"]').value;
    const data = `title=${titleCase}&description=${descriptionCase}`;
    console.log(data);
    $.ajax({
        url: `/cases/${thisId}/`,
        type: 'PUT',
        data: data,
        success: function (response) {
            const divWithChange = container.querySelector('div[data-id="' + thisId + '"]');
            const $ptitle = divWithChange.querySelector('case_title');
            $ptitle.innerHTML = titleCase;
            const $pdescription = divWithChange.querySelector('case_description');
            $pdescription.innerHTML = descriptionCase;
        }

    });
}

function openRedForm(elem, position) {

    const fragment = document.createDocumentFragment();

    const redForm = document.createElement('div');
    redForm.classList.add('red_form');
    redForm.setAttribute('data-id', elem.getAttribute('data-id'));

    const par = document.createElement('p');
    par.id = 'heading';
    par.innerText = 'Редактирование задачи';

    const redactionPlace = document.createElement('div');
    redactionPlace.id = 'edit_place';

    const title_input = document.createElement('input');
    title_input.type = 'text';
    title_input.name = 'title';
    title_input.value = elem.querySelector('.case_title').textContent;

    const description_input = document.createElement('input');
    description_input.type = 'text';
    description_input.name = 'description';
    description_input.value = elem.querySelector('.case_description').textContent;


    const sendButton = document.createElement('button');
    sendButton.id = 'send_button';
    sendButton.textContent = 'Изменить задачу';

    const canselButton = document.createElement('button');
    canselButton.id = 'cansel_button';
    canselButton.textContent = 'Отмена';

    redactionPlace.appendChild(title_input);
    redactionPlace.appendChild(description_input);

    redForm.appendChild(par);
    redForm.appendChild(redactionPlace);

    redForm.appendChild(sendButton);
    redForm.appendChild(canselButton);

    fragment.appendChild(redForm);

    position.appendChild(fragment);
}



