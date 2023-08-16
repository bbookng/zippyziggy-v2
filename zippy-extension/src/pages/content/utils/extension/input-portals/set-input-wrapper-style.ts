// 입력창 focus 시 border 스타일 지정
export const setInputWrapperStyle = (parent) => {
  const $parent = parent;
  $parent.style.paddingTop = 0;
  $parent.style.paddingRight = '1rem';
  $parent.classList.add('ZP_inputFocus');

  const handleFocus = () => {
    $parent.classList.add('ZP_inputFocus');
  };

  const handleBlur = () => {
    $parent.classList.remove('ZP_inputFocus');
  };

  document.querySelector('textarea')?.addEventListener('focus', handleFocus);
  document.querySelector('textarea')?.addEventListener('blur', handleBlur);
};
