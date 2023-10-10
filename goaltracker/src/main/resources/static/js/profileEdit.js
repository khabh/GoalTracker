const existingErrors = {interests: false, introduction: false, nickname: false};
document.addEventListener("DOMContentLoaded", function() {
    new Tagify(document.getElementById('interests'));
    const saveButton = document.getElementById("save-button");

    saveButton.addEventListener("click", function(event) {
        event.preventDefault();

        const form = document.getElementById('profile-form');
        refreshHiddenInterests(form);

        fetch(form.action, {
            method: 'PUT',
            body: new FormData(form)
        }).then(response => {
            if (!response.ok) {
                response.json().then(errors => {
                    if (errors.hasOwnProperty("introduction") && !existingErrors.introduction) {
                        let introductionField = document.querySelector("#self-introduction");
                        let introductionError = createErrorMessage(errors.introduction);

                        introductionField.classList.add("error-input");
                        introductionField.parentNode.appendChild(introductionError);
                        existingErrors.introduction = true;
                    } else if (!errors.hasOwnProperty("introduction") && existingErrors.introduction) {
                        deleteIntroductionError(form);
                        existingErrors.introduction = false;
                    }

                    if (errors.hasOwnProperty("interests") && !existingErrors.interests) {
                        let interestsFieldParentNode = document.querySelector(".tagify").parentNode;
                        let interestsError = createErrorMessage(errors.interests);

                        interestsFieldParentNode.setAttribute("id", "error-tag");
                        interestsFieldParentNode.appendChild(interestsError);
                        existingErrors.interests = true;
                    } else if (!errors.hasOwnProperty("interests") && existingErrors.interests) {
                        deleteInterestsError(form);
                        existingErrors.interests = false;
                    }

                    if (errors.hasOwnProperty("nickname") && !existingErrors.nickname) {
                        let nicknameField = document.querySelector("#nickname");
                        let introductionError = createErrorMessage(errors.nickname);

                        nicknameField.classList.add("error-input");
                        nicknameField.parentNode.appendChild(introductionError);
                        existingErrors.introduction = true;
                    } else if (!errors.hasOwnProperty("nickname") && existingErrors.nickname) {
                        deleteNicknameError(form);
                        existingErrors.nickname = false;
                    }
                });
            } else {
                Swal.fire({
                    icon: 'success',
                    title: '변경 사항이 저장되었습니다.',
                });
            }
        }).catch();
    });

    function deleteNicknameError(form) {
        const nickname = form.querySelector('input[class="error-input"]');
        nickname.removeAttribute("class");
        const formGroup = nickname.parentNode;
        formGroup.removeChild(formGroup.querySelector('span[class="error-message"]'));
    }


    function deleteIntroductionError(form) {
        const introduction = form.querySelector('textarea[class="error-input"]');
        introduction.removeAttribute("class");
        const formGroup = introduction.parentNode;
        formGroup.removeChild(formGroup.querySelector('span[class="error-message"]'));
    }

    function deleteInterestsError(form) {
        const formGroup = form.querySelector('div[id="error-tag"]');
        formGroup.removeAttribute('id');
        const errorMessage = formGroup.querySelector('span[class="error-message"]');
        formGroup.removeChild(errorMessage);
    }

    function refreshHiddenInterests(form) {
        const existingInputs = form.querySelectorAll('input[type="hidden"][name="interests"]');
        existingInputs.forEach(input => {
            input.remove();
        });

        const tags = Array.from(document.getElementsByClassName('tagify__tag'));

        if (tags.length > 0) {
            tags.forEach(tag => {
                const input = `<input type="hidden" name="interests" value="${tag.getAttribute('value')}">`;
                form.insertAdjacentHTML('beforeend', input);
            });
        }
    }

    function createErrorMessage(text) {
        const errorMessageElement = document.createElement("span");
        errorMessageElement.textContent = text;
        errorMessageElement.setAttribute("class", "error-message");

        return errorMessageElement;
    }
});