/* GLOBAL VARIABLES */
const PIZZA_URL = 'http://localhost:8080/api/pizzas';
const contentElement = document.getElementById('content');
const toggleForm = document.getElementById('toggle-form');
const pizzaForm = document.getElementById('pizza-form');

const getPizzaList = async () => {
    const response = await fetch(PIZZA_URL);
    return response;
};

// createDeleteBtn = async () => {
//     let btn = "";
//     btn = `<button class="btn btn-danger">Elimina</button>`;
//     return btn;
// }

const createPizza = (pizza) => {
    return `<div class="col-4">
              <div class="card h-100">
                  <div class="card-body">
                      <h5 class="card-title">${pizza.name}</h5>
                      <h6 class="card-subtitle mb-2 text-body-secondary">${pizza.price}</h6>
                      <p class="card-text d-flex justify-content-between">
                         ${pizza.description}
                      </p>
                      <button data-id="${pizza.id}" class="btn btn-danger">Elimina</button>
                  </div>
              </div>
      </div>`;
};

const buildPizzaCols = (array) => {
    let pizzaList = `<div class="row gy-3">`;
    array.forEach((pizza) => {
        pizzaList += createPizza(pizza);
    });
    pizzaList += '</div>';
    return pizzaList;

}

const loadData = async () => {
    const response = await getPizzaList();
    if (response.ok) {
        const data = await response.json();
        contentElement.innerHTML = buildPizzaCols(data);
        const deleteBtns = document.querySelectorAll('button[data-id]');
        for (let btn of deleteBtns) {
            btn.addEventListener('click', () => {
                deletePizza(btn.dataset.id);
            });
        }
    } else {
        console.log('ERROR');
    }
};

const postPizza = async (jsonData) => {
    const fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: jsonData,
    };
    const response = await fetch(PIZZA_URL + "/create", fetchOptions);
    return response;
};

const savePizza = async (event) => {
    event.preventDefault();

    const formData = new FormData(event.target);
    const formDataObj = Object.fromEntries(formData.entries());

    const formDataJson = JSON.stringify(formDataObj);

    const response = await postPizza(formDataJson);

    const responseBody = await response.json();
    if (response.ok) {
        loadData();
        event.target.reset();
    } else {
        console.log('ERROR');
        console.log(response.status);
        console.log(responseBody);
    }
};

const deletePizzaById = async (pizzaId) => {
    const response = await fetch(PIZZA_URL + '/' + pizzaId, { method: 'DELETE' });
    return response;
}

const deletePizza = async (pizzaId) => {
    const response = await deletePizzaById(pizzaId);
    if (response.ok) {
        loadData();
    } else {
        console.log('ERROR');
        console.log(response.status);
    }
};

const toggleFormVisibility = () => {
    document.getElementById('form').classList.toggle('d-none');
};

toggleForm.addEventListener('click', toggleFormVisibility);
pizzaForm.addEventListener('submit', savePizza);
loadData();



