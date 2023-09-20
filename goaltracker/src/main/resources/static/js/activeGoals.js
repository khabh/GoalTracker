function toggleAndSubmit(form) {
    const checkbox = form.querySelector('input[type="checkbox"]')
    const hiddenInput = form.querySelector('input[type="hidden"]');
    hiddenInput.value = checkbox.checked ? "true" : "false";
    fetch(form.action, {
        method: 'PATCH',
        body: new FormData(form)
    });
}

function followUser(form) {
    fetch(`/goal-tracker/follows`, {
        method: 'POST',
        body: new FormData(form)
    }).then(response => {
        if (!response.ok) {
            response.json().then(error => {
                notionError("요청 실패", error["message"]);
            })
        } else {
            location.reload();
        }
    });
}

function notionError(title, detailMessage) {
    Swal.fire({
        icon: 'error',
        title: title,
        text: detailMessage
    });
}