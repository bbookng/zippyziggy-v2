// regenerate 버튼을 반환하는 함수
export const findRegenerateButton = () => {
  const regenerateButtonSelector = 'button.btn.relative.btn-neutral.border-0.md\\:border';
  return document.querySelector(regenerateButtonSelector);
};
