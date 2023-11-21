// const btn = document.getElementById("btn").addEventListener('click', request());
// if(btn) {

// }
const serverUrl = 'http://localhost:8080/teste';

// Dados a serem enviados
const dataToSend = 'Olá, servidor!';

// Configuração da requisição
const requestOptions = {
    method: 'POST', // ou 'GET' dependendo do método que você está usando no servidor
    headers: {
        'Content-Type': 'text/plain', // ou o tipo de conteúdo apropriado
    },
    body: dataToSend
};
function request() {

    // Realizar a requisição
    fetch(serverUrl, requestOptions)
        .then(response => response.text())
        .then(data => {
            console.log('Dados recebidos do servidor:', data);
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
        });
}