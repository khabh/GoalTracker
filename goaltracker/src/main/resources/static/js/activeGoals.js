function toggleAndSubmit(form) {
    const checkbox = form.querySelector('input[type="checkbox"]')
    const hiddenInput = form.querySelector('input[type="hidden"]');
    hiddenInput.value = checkbox.checked ? "true" : "false";
    fetch(form.action, {
        method: 'PATCH',
        body: new FormData(form)
    });
}