// regenerate 버튼을 반환하는 함수
export const findRegenerateButton = () => {
  const regenerateButtonSelector = 'button.relative.-z-0';
  const $regenerateButton = document.querySelector('form').querySelector(regenerateButtonSelector);

  return $regenerateButton;
};
