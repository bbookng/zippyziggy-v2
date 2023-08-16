// GPT 사이트의 맨 아래로 가는 버튼의 위치를 조정하는 함수
export const adjustToBottomButtonPosition = (targetElement: HTMLElement) => {
  const toBottomButton = targetElement.querySelector('button.absolute:not(#ZP_toTopButton)');

  if (toBottomButton) {
    if (toBottomButton.classList.contains('bottom-1')) return;
    toBottomButton.classList.remove('bottom-[124px]', 'md:bottom-[180px]', 'lg:bottom-[120px]');
    toBottomButton.classList.add('bottom-1', 'right-[100px]');
  }
};
