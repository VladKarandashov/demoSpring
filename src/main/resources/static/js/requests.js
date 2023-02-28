async function getJsonFromRequest(url) {
    const response = await fetch(url);
    if (!response.ok) {
        throw new Error("HTTP error " + response.status);
    }
    return await response.json();
}

function getItemsJson(genre, country, ageLimit) {
    if (genre == null) genre='';
    if (country == null) country='';
    if (ageLimit == null) ageLimit='';
    return getJsonFromRequest('/search/items?genre='+genre+'&country='+country+'&ageLimit='+ageLimit)
}

function getItemsObject(genre, country, ageLimit) {
    let data = getItemsJson(genre, country, ageLimit)
    console.log("Из вызванной функции получено: "+data)
    console.log(data)
    data.then(json => {
            data = json
        })
        .catch(error => {
            throw new Error("HTTP error"+error)
        });
    console.log("Попытался извлечь json: "+data)
    console.log(data)
    // const dataJson = listStr.replaceAll("&quot;", "\"")
    // console.log(dataJson)
    // const data = JSON.parse(dataJson)
    const dataGenre = data.genreList;
    const dataCountry = data.countryList;
    const dataAgeLimit = data.ageLimitList;

    console.log("Жанры")
    console.log(dataGenre)

    const genreObjs = [];
    for (let item of dataGenre) {
        const genreObj = new Genre(item.id, item.title);
        genreObjs.push(genreObj);
    }
    const countryObjs = [];
    for (let item of dataCountry) {
        const countryObj = new Country(item.id, item.title);
        countryObjs.push(countryObj);
    }
    const ageLimitObjs = [];
    for (let item of dataAgeLimit) {
        const ageLimitObj = new AgeLimit(item.id, item.category);
        ageLimitObjs.push(ageLimitObj);
    }

    console.log(genreObjs)
    console.log(countryObjs)
    console.log(ageLimitObjs)
    return new ItemsDTO(genreObjs, countryObjs, ageLimitObjs)
}

console.log(getItemsObject(null, null, null))