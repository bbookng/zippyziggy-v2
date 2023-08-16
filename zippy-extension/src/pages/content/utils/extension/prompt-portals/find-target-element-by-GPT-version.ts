/* eslint-disable no-param-reassign */
const addRootWrapperToTargetElement = ($target: HTMLElement) => {
  const rootWrapper = document.createElement('div');
  rootWrapper.className = 'px-2 py-10 relative w-full flex flex-col h-full';
  ($target.parentElement.querySelector('div:nth-of-type(2)') as HTMLElement).style.display = 'none';
  $target = $target.parentElement;
  $target.appendChild(rootWrapper);
  return rootWrapper;
};

// GPT 유료버전(isPlus가 참일 때 대상 요소를 찾는 함수)
const findPlusTarget = ($parent: Element): HTMLElement | null => {
  const $target = $parent.querySelector(
    'div.px-2.w-full.flex.flex-col.py-2.md\\:py-6.sticky.top-0'
  ) as HTMLElement;
  return $target ? addRootWrapperToTargetElement($target.parentElement) : null;
};

// GPT 무료버전(isPlus가 거짓일 때 대상 요소를 찾는 함수)
const findNonPlusTarget = ($parent: Element): HTMLElement | null => {
  const $target = $parent.querySelector('div.align-center.flex.h-full') as HTMLElement;
  return $target ? addRootWrapperToTargetElement($target) : null;
};

// GPT 버전에 따라 대상 요소를 찾아 반환하는 함수
export const findTargetElementByGPTVersion = ($parent: Element, isPlus: boolean) => {
  return isPlus ? findPlusTarget($parent) : findNonPlusTarget($parent);
};
