document.addEventListener("DOMContentLoaded", function() {
  const nacionalidades = [
    "Afgana", "Albanesa", "Alemana", "Andorrana", "Angoleña", "Antiguana",
    "Argentina", "Armenia", "Australiana", "Austriaca", "Bahameña", "Bangladesí",
    "Barbadense", "Belga", "Beliceña", "Beninesa", "Bielorrusa", "Boliviana",
    "Bosnia", "Botsuana", "Brasileña", "Británica", "Búlgara", "Camboyana",
    "Camerunesa", "Canadiense", "Chilena", "China", "Colombiana", "Congoleña",
    "Costarricense", "Croata", "Cubana", "Checa", "Danesa", "Dominicana",
    "Ecuatoriana", "Egipcia", "Salvadoreña", "Emiratí", "Eslovaca", "Eslovena",
    "Española", "Estadounidense", "Estonia", "Etíope", "Filipina", "Finlandesa",
    "Francesa", "Griega", "Guatemalteca", "Haitiana", "Hondureña", "Húngara",
    "India", "Indonesia", "Irlandesa", "Islandesa", "Israelí", "Italiana",
    "Jamaicana", "Japonesa", "Jordana", "Kazaja", "Keniana", "Letona", "Libanesa",
    "Libia", "Lituana", "Luxemburguesa", "Malasia", "Maliense", "Marroquí",
    "Mexicana", "Moldava", "Monegasca", "Mongola", "Namibia", "Nepalesa", 
    "Neozelandesa", "Nicaragüense", "Nigeriana", "Noruega", "Panameña", "Paraguaya",
    "Peruana", "Polaca", "Portuguesa", "Puertorriqueña", "Qatarí", "Rumana",
    "Rusa", "Saudí", "Senegalesa", "Serbia", "Singapurense", "Siria", "Somalí",
    "Sudafricana", "Sudanesa", "Sueca", "Suiza", "Tailandesa", "Tanzana", 
    "Tunecina", "Turca", "Ucraniana", "Uruguaya", "Venezolana", "Vietnamita", 
    "Yemení", "Zambiana", "Zimbabuense"
  ];

  const select = document.getElementById("nacionalidad");

  nacionalidades.forEach(nac => {
    const option = document.createElement("option");
    option.value = nac;
    option.textContent = nac;
    select.appendChild(option);
  });
});