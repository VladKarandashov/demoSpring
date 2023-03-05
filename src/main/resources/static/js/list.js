$(document).ready(function () {
   //global
   const table = $('body table[dir="LTR"]');
   const tBody = table.find("tbody");
   const tableTr = tBody.find("tr");
   const tableTd = tableTr.find("td");

   const tableClone = $('body table[dir="LTR"]').clone();

   let tableCloneArrSet = [],
      tableCloneTdArr = [];

   const tableTrArr = [],
      tdArr = [];

   tableTrArr.push(tableTr);

   const genre = $("#genre");
   const country = $("#country");
   const ageLimit = $("#age_limit");

   let genreSet = new Set();
   let countrySet = new Set();
   let ageLimitSet = new Set();

   let genreOptionSelected = "Любой",
      countryOptionSelected = "Любой",
      ageLimitOptionSelected = "Любой";

   let i = 0;
   let spaceT = "\t",
      spaceN = "\n",
      result = [];

   function createSelect() {
      genre.empty();
      country.empty();
      ageLimit.empty();

      genre.prepend(`<option value="0">Любой</option>`);
      country.prepend(`<option value="0">Любой</option>`);
      ageLimit.prepend(`<option value="0">Любой</option>`);

      let countOfGenreSet = 1,
         countOfCountrySet = 1,
         countOfAgeLimitSet = 1;

      tdArr.map((item, index) => {
         genreSet.add(item[8]);
         countrySet.add(item[7]);
         ageLimitSet.add(item[9]);
      });

      for (let item of genreSet) {
         genre.append(
            `<option value="${countOfGenreSet}">${countOfGenreSet}.  ${item}</option>`
         );
         countOfGenreSet++;
      }

      for (let item of countrySet) {
         country.append(
            `<option value="${countOfCountrySet}">${countOfCountrySet}.  ${item}</option>`
         );
         countOfCountrySet++;
      }

      for (let item of ageLimitSet) {
         ageLimit.append(
            `<option value="${countOfAgeLimitSet}">${countOfAgeLimitSet}.  ${item}</option>`
         );
         countOfAgeLimitSet++;
      }
   }

   function getOption() {
      let countOfGenreSet = 1,
         countOfCountrySet = 1,
         countOfAgeLimitSet = 1;

      for (let item of genreSet) {
         genre.append(
            `<option value="${countOfGenreSet}">${countOfGenreSet}.  ${item}</option>`
         );
         countOfGenreSet++;
      }

      for (let item of countrySet) {
         country.append(
            `<option value="${countOfCountrySet}">${countOfCountrySet}.  ${item}</option>`
         );
         countOfCountrySet++;
      }

      for (let item of ageLimitSet) {
         ageLimit.append(
            `<option value="${countOfAgeLimitSet}">${countOfAgeLimitSet}.  ${item}</option>`
         );
         countOfAgeLimitSet++;
      }

      genre.prepend(`<option value="0">Любой</option>`);
      country.prepend(`<option value="0">Любой</option>`);
      ageLimit.prepend(`<option value="0">Любой</option>`);

      console.log(genreOptionSelected);
      console.log(countryOptionSelected);
      console.log(ageLimitOptionSelected);

      if (genreOptionSelected === "Любой") {
         genre.find("option:first-child").attr("selected", "selected");
         console.log(genre.find("option"));
      }
      if (countryOptionSelected === "Любой") {
         country.find("option:first-child").attr("selected", "selected");
         console.log(country.find("option"));
      }
      if (ageLimitOptionSelected === "Любой") {
         ageLimit.find("option:first-child").attr("selected", "selected");
         console.log(ageLimit.find("option"));
      }
   }

   genre.on("change", function () {
      if (parseInt($(this).val()) === 0) {
         genreOptionSelected = "Любой";
      } else {
         genreOptionSelected = $("#genre option:selected")
            .text()
            .substring(3)
            .trim();
      }
      render();
   });
   country.on("change", function () {
      if (parseInt($(this).val()) === 0) {
         countryOptionSelected = "Любой";
      } else {
         countryOptionSelected = $("#country option:selected")
            .text()
            .substring(3)
            .trim();
      }
      render();
   });
   ageLimit.on("change", function () {
      if (parseInt($(this).val()) === 0) {
         ageLimitOptionSelected = "Любой";
      } else {
         ageLimitOptionSelected = $("#age_limit option:selected")
            .text()
            .substring(3)
            .trim();
      }

      render();
   });

   function firstRender() {
      table.css("display", "none");

      $("body").append(tableClone);
      tableClone
         .addClass("resultTable")
         .css("display", "table")
         .css("width", "100%");
   }

   function convertToArr() {
      tableTrArr.map((tr) =>
         tr.map((td) => {
            let tdText = tr[td].innerText;

            let tempestString = tdText.split(spaceT);

            tdArr.push(tempestString);

            return tdArr;
         })
      );
   }

   convertToArr();

   function render() {
      genre.empty();
      country.empty();
      ageLimit.empty();

      genreSet.clear();
      countrySet.clear();
      ageLimitSet.clear();

      if (
         genreOptionSelected == "Любой" &&
         countryOptionSelected == "Любой" &&
         ageLimitOptionSelected == "Любой"
      ) {
         tdArr.map((tr, index) => {
            let tableTr = document.createElement("tr");

            genreSet.add(tr[8]);
            countrySet.add(tr[7]);
            ageLimitSet.add(tr[9]);

            tr.map((td) => {
               let tableTd = document.createElement("td");
               tableTd.innerHTML = td;

               tableTr.append(tableTd);
            });

            tableClone.find("tbody").append(tableTr);
         });
         getOption();
         return;
      } else {
         tdArr.map((item) => {
            if (
               genreOptionSelected !== "Любой" &&
               countryOptionSelected !== "Любой" &&
               ageLimitOptionSelected !== "Любой"
            ) {
               if (
                  item[8] === genreOptionSelected &&
                  item[7] === countryOptionSelected &&
                  item[9] === ageLimitOptionSelected
               ) {
                  result.push(item);
               }
            } else if (
               genreOptionSelected !== "Любой" &&
               countryOptionSelected !== "Любой"
            ) {
               if (
                  item[8] === genreOptionSelected &&
                  item[7] === countryOptionSelected
               ) {
                  result.push(item);
               }
            } else if (
               genreOptionSelected !== "Любой" &&
               ageLimitOptionSelected !== "Любой"
            ) {
               if (
                  item[8] === genreOptionSelected &&
                  item[9] === ageLimitOptionSelected
               ) {
                  result.push(item);
               }
            } else if (
               countryOptionSelected !== "Любой" &&
               ageLimitOptionSelected !== "Любой"
            ) {
               if (
                  item[7] === countryOptionSelected &&
                  item[9] === ageLimitOptionSelected
               ) {
                  result.push(item);
               }
            } else if (genreOptionSelected !== "Любой") {
               if (item[8] === genreOptionSelected) {
                  result.push(item);
               }
            } else if (countryOptionSelected !== "Любой") {
               if (item[7] === countryOptionSelected) {
                  result.push(item);
               }
            } else if (ageLimitOptionSelected !== "Любой") {
               if (item[9] === ageLimitOptionSelected) {
                  result.push(item);
               }
            }
         });
      }

      tableClone.find("tbody").empty();

      result.map((item, index) => {
         let tr = document.createElement("tr");
         let counter = index + 1;
         genreSet.add(item[8]);
         countrySet.add(item[7]);
         ageLimitSet.add(item[9]);
         item.map((td) => {
            //addToSelect

            let tableTd = document.createElement("td");
            tableTd.innerHTML = td;

            tr.append(tableTd);
         });

         tableClone.find("tbody").append(tr);

         tableClone
            .find("tbody tr:nth-child(" + counter + ") td:first-child")
            .html(counter);
      });

      getOption();
      clearArr();
   }

   function clearArr() {
      result = [];
   }
   createSelect();
   firstRender();
});
