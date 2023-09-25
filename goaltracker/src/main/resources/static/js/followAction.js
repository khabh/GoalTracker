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

function unfollowUser(userId) {
    fetch(`/goal-tracker/users/${userId}/follows`, {
        method: 'DELETE',
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