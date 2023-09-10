flatpickr("#due-date", {
    dateFormat: "Y-m-d",
    altInput: true,
    altFormat: "F j, Y",
    minDate: "today",
    monthSelectorType : 'static',
    them: "material_orange",
    appendTo: document.querySelector(".new-goal-form"),
});
function addItem() {
    const checklistItems = document.getElementById("checklistItems");
    const newItem = document.createElement("div");
    newItem.className = "checklist-item";
    newItem.innerHTML = `
                <input type="text" class="item-input" name="checklists" placeholder="내용을 입력하세요.">
                <button type="button" class="remove-button" onclick="removeItem(this)">삭제</button>
            `;
    checklistItems.appendChild(newItem);
}

function removeItem(button) {
    const item = button.parentNode;
    item.parentNode.removeChild(item);
}

const checklistInput = document.querySelector(".item-input");
checklistInput.addEventListener("keyup", function(event) {
    if (event.key === "Enter" && checklistInput.value.trim() !== "") {
        addItem();
        checklistInput.value = "";
    }
});