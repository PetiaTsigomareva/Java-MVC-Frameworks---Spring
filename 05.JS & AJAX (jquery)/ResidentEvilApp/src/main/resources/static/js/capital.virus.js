function fetchData() {
    $('#viruses-radio').click(() => {
        fetch('/viruses')
.then((response) => response.json())
.then((json) => {

        $('#title').text('All Viruses');
    $('.container #data-container').remove();
    $('.container').append('<div id="data-container" class="mt-5"></div>');

    let table =
        `<table class="table">` +
        `<thead>` +
        `<tr class="text-center">` +
        `<th scope="col">#</th>` +
        `<th scope="col">Name</th>` +
        `<th scope="col">Magnitude</th>` +
        `<th scope="col">Released On</th>` +
        `<th scope="col">Actions</th>` +
        `</tr>` +
        `</thead>` +
        `<tbody>`;

    json.forEach((x, y) => {

        table +=
            `<tr class="text-center">` +
            `<th scope="row">${y + 1}</th>` +
            `<td>${x.name}</td>` +
            `<td>${x.magnitude}</td>` +
            `<td>${x.releasedOn}</td>` +
            `<td class="d-flex justify-content-between">` +
            `<a class="btn re-background" href="/viruses/edit/${x.id}">Edit</a>` +
            `<a class="btn re-background" href="/viruses/delete/${x.id}">Delete</a>` +
            `</td>` +
            `</tr>`;
});

    table +=
        `</tbody>` +
        `</table>`;

    $('#data-container').append(table);
});
});

    $('#capitals-radio').click(() => {
        fetch('/capitals')
.then((response) => response.json())
.then((json) => {

        $('#title').text('All Capitals');
    $('.container #data-container').remove();
    $('.container').append('<div id="data-container" class="mt-5"></div>');

    let table =
        `<table class="table">` +
        `<thead>` +
        `<tr class="text-center">` +
        `<th scope="col">#</th>` +
        `<th scope="col">Name</th>` +
        `<th scope="col">Latitude</th>` +
        `<th scope="col">Longitude</th>` +
        `</tr>` +
        `</thead>` +
        `<tbody>`;

    json.forEach((x, y) => {

        table +=
            `<tr class="text-center">` +
            `<th scope="row">${y + 1}</th>` +
            `<td>${x.name}</td>` +
            `<td>${x.latitude}</td>` +
            `<td>${x.longitude}</td>` +
            `</tr>`;
});

    table +=
        `</tbody>` +
        `</table>`;

    $('#data-container').append(table);
});
});
}
fetchData();
