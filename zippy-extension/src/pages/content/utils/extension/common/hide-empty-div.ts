// 대화 화면에서 마지막 부분에 빈 div를 숨기는 함수
import { ZP_INPUT_WRAPPER_ID } from '@pages/constants';

export const hideEmptyDiv = (targetElement: Element) => {
  if (
    targetElement.id === ZP_INPUT_WRAPPER_ID ||
    targetElement.className === 'flex-1 overflow-hidden' ||
    targetElement.className ===
      'h-full flex ml-1 md:w-full md:m-auto md:mb-2 gap-0 md:gap-2 justify-center' ||
    targetElement.className === 'flex flex-col text-sm dark:bg-gray-800 h-full'
  ) {
    const $unlessElement = document.querySelector(
      'div.h-32.md\\:h-48.flex-shrink-0'
    ) as HTMLDivElement;
    if ($unlessElement) {
      $unlessElement.style.display = 'none';
    }
  }
};
