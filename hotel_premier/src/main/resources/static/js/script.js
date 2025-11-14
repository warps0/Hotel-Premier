document.addEventListener("DOMContentLoaded", function() {
  const nacionalidades = [
    "Afganistán", "Albania", "Alemania", "Andorra", "Angola", "Antigua y Barbuda",
    "Argentina", "Armenia", "Australia", "Austria", "Bahamas", "Bangladés",
    "Barbados", "Bélgica", "Belice", "Benín", "Bielorrusia", "Bolivia",
    "Bosnia y Herzegovina", "Botsuana", "Brasil", "Reino Unido", "Bulgaria", "Camboya",
    "Camerún", "Canadá", "Chile", "China", "Colombia", "Congo",
    "Costa Rica", "Croacia", "Cuba", "República Checa", "Dinamarca", "República Dominicana",
    "Ecuador", "Egipto", "El Salvador", "Emiratos Árabes Unidos", "Eslovaquia", "Eslovenia",
    "España", "Estados Unidos", "Estonia", "Etiopía", "Filipinas", "Finlandia",
    "Francia", "Grecia", "Guatemala", "Haití", "Honduras", "Hungría",
    "India", "Indonesia", "Irlanda", "Islandia", "Israel", "Italia",
    "Jamaica", "Japón", "Jordania", "Kazajistán", "Kenia", "Letonia", "Líbano",
    "Libia", "Lituania", "Luxemburgo", "Malasia", "Mali", "Marruecos",
    "México", "Moldavia", "Mónaco", "Mongolia", "Namibia", "Nepal",
    "Nueva Zelanda", "Nicaragua", "Nigeria", "Noruega", "Panamá", "Paraguay",
    "Perú", "Polonia", "Portugal", "Puerto Rico", "Catar", "Rumania",
    "Rusia", "Arabia Saudita", "Senegal", "Serbia", "Singapur", "Siria", "Somalia",
    "Sudáfrica", "Sudán", "Suecia", "Suiza", "Tailandia", "Tanzania",
    "Túnez", "Turquía", "Ucrania", "Uruguay", "Venezuela", "Vietnam",
    "Yemen", "Zambia", "Zimbabue"
  ];

  const paises = nacionalidades;

  const select = document.getElementById("nacionalidad");
  const altSelect = document.getElementById("pais");

  nacionalidades.forEach(nac => {
    const option = document.createElement("option");
    option.value = nac;
    option.textContent = nac;
    select.appendChild(option);
  });

  select.value = "Argentina";

  paises.forEach(nac => {
    const option = document.createElement("option");
    option.value = nac;
    option.textContent = nac;
    altSelect.appendChild(option);
  });

  altSelect.value = "Argentina";

});

const overlay = document.getElementById('overlay');
    const cargarBtn = document.getElementById('cargar');
    const noBtn = document.getElementById('noBtn');
    const siBtn = document.getElementById('siBtn');

    cargarBtn.addEventListener('click', () => {
      setTimeout(() => {
        overlay.style.display = 'flex';
      }, 500);
    });

