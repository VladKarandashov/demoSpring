function removeOptions(selectElement) {
    console.log("callRemoveOptions")
    let i, L = selectElement.options.length - 1;
    for(i = L; i >= 0; i--) {
        selectElement.remove(i);
    }
}

function genre () {
    console.log("call genre")
    let select = document.getElementById('genre')
    removeOptions(select)
    const genresFromModel = [[${genres}]];
    console.log(genresFromModel)
    genresFromModel.forEach(option =>
        select.options.add(
            new Option(option.title, option.id)
        )
    );
}

genre()