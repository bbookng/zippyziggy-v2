// 맨 위로 가는 버튼이 생길 조건을 검사하는 함수
import { ZP_INPUT_WRAPPER_ID } from '@pages/constants';

export const shouldAddToTopButton = (targetElement) => {
  // react-scroll-to-bottom 컴포넌트가 존재하는지 확인
  const scrollComponent = document.querySelector("[class^='react-scroll-to-bottom--css']")
    ?.children[0];
  return (
    scrollComponent &&
    (targetElement.id === ZP_INPUT_WRAPPER_ID ||
      targetElement.className === 'ZP_prompt-container__inner' ||
      targetElement.className === 'flex-1 overflow-hidden')
  );
};
