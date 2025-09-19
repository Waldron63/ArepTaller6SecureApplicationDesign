document.getElementById('form').addEventListener('submit', e => {
    e.preventDefault();
    createProperties();
});

function createProperties() {
    const p = {
        address: document.getElementById('address').value,
        price: parseFloat(document.getElementById('price').value),
        size: parseFloat(document.getElementById('size').value),
        description: document.getElementById('description').value
    };

    fetch('/api/properties/', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(p)
    })
        .then(r => r.json())
        .then(() => {
            msg('Added!', 'success');
            loadProperties();
            document.getElementById('form').reset();
        })
        .catch(() => msg('Error adding.', 'error'));
}

function loadProperties() {
    fetch('/api/properties/')
        .then(response => response.json())
        .then(properties => {
            const propertyTableBody = document.querySelector('#tbl tbody');
            propertyTableBody.innerHTML = ''; // limpiar tabla

            properties.forEach(property => {
                const row = document.createElement('tr');

                row.innerHTML = `
                    <td>${property.id}</td>
                    <td>${property.address}</td>
                    <td>$${property.price.toLocaleString()}</td>
                    <td>${property.size} m²</td>
                    <td>${property.description}</td>
                    <td>
                        <button class="edit" onclick="editProperties(${property.id})">Editar</button>
                        <button class="del" onclick="removeProperties(${property.id})">Eliminar</button>
                    </td>
                `;

                propertyTableBody.appendChild(row);
            });
        })
        .catch(() => msg('Error al cargar las propiedades.', 'error'));
}

function removeProperties(id) {
    fetch(`/api/properties/${id}`, { method: 'DELETE' })
        .then(() => {
            msg('Deleted!', 'success');
            loadProperties();
        })
        .catch(() => msg('Error deleting.', 'error'));
}

function editProperties(id) {
    fetch(`/api/properties/${id}`)
        .then(r => r.json())
        .then(p => {
            document.getElementById('address').value = p.address;
            document.getElementById('price').value = p.price;
            document.getElementById('size').value = p.size;
            document.getElementById('description').value = p.description;

            const btn = document.querySelector('#form button');
            btn.textContent = "Update";
            btn.type = "button";
            btn.onclick = () => updateProperties(id);
        })
        .catch(() => msg('Error loading item.', 'error'));
}

function updateProperties(id) {
    const p = {
        address: document.getElementById('address').value,
        price: parseFloat(document.getElementById('price').value),
        size: parseFloat(document.getElementById('size').value),
        description: document.getElementById('description').value
    };

    fetch(`/api/properties/${id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(p)
    })
        .then(r => r.json())
        .then(() => {
            msg('Updated!', 'success');
            loadProperties();
            document.getElementById('form').reset();
            const btn = document.querySelector('#form button');
            btn.textContent = "Add";
            btn.type = "submit";
            btn.onclick = null;
        })
        .catch(() => msg('Error updating.', 'error'));
}

function msg(t, type) {
    const div = document.getElementById('msg');
    div.textContent = t;
    div.className = `message ${type}`;
}

loadProperties();
