export class DOMUtils {
  static setText(id, value) {
    const el = document.getElementById(id);
    if (el) el.textContent = value || "—";
  }

  static getCheckedValue(name) {
    const el = document.querySelector(`input[name="${name}"]:checked`);
    return el ? el.value : null;
  }

  static resetForm(formSelector) {
    const form = document.querySelector(formSelector);
    if (form) form.reset();
  }

  static scrollToTop() {
    window.scrollTo({ top: 0, behavior: "smooth" });
  }
}
