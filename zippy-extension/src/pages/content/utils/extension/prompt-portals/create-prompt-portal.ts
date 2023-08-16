// react-scroll-to-bottom 클래스를 가진 요소를 반환하는 함수
import intervalForFindElement from '@pages/content/utils/extension/intervalForFindElement';
import { findTargetElementByGPTVersion } from '@pages/content/utils/extension/prompt-portals/find-target-element-by-GPT-version';
import { findRegenerateButton } from '@pages/content/utils/extension/common/find-regenerate-button';
import { ZP_PROMPT_CONTAINER_ID } from '@pages/constants';

export const findParentElementWithReactScrollClass = () => {
  const reactScrollSelector = '[class*="react-scroll-to-bottom"]';
  return document.querySelector(reactScrollSelector);
};

// 포탈 컨테이너를 생성하는 함수
export const createPromptContainer = () => {
  const $promptListPortals = document.getElementById(ZP_PROMPT_CONTAINER_ID);
  if ($promptListPortals) return null;

  const $portalContainer = document.createElement('div');
  $portalContainer.id = ZP_PROMPT_CONTAINER_ID;
  return $portalContainer;
};

export const createPromptPortal = (setPortalContainer) => {
  // react-scroll-to-bottom 클래스를 가진 부모 요소를 찾음
  const $parent = findParentElementWithReactScrollClass();
  if (!$parent) return;

  // 응답 재생성 버튼을 찾음
  const $regenerateButton = findRegenerateButton();
  if ($regenerateButton) return;

  // 포탈 컨테이너를 생성함
  const $portalContainer = createPromptContainer();
  if (!$portalContainer) return;

  const $title = document.querySelector('h1.text-4xl') as HTMLElement;
  let isPlus = false;
  if ($title) {
    $title.style.display = 'none';
    isPlus = $title.textContent === 'ChatGPTPlus';
  }
  ($title.parentElement.nextSibling as HTMLElement).style.display = 'none';

  // ChatGPTPlus 여부를 확인하고, 대상 요소를 찾음
  const $target = findTargetElementByGPTVersion($parent, isPlus);
  // 대상 요소를 찾지 못한 경우 포탈 생성 실패
  if (!$target) {
    setPortalContainer(null);
    return;
  }

  intervalForFindElement('div.sticky.flex', ($target) => {
    const 지피티_버전_선택 = $target;
    지피티_버전_선택.style.zIndex = '100';
  });

  document.querySelector('.pb-64').classList.remove('pb-64');

  // 포탈 컨테이너를 대상 요소에 추가하고, state를 업데이트하여 포탈 컨테이너를 반환함
  $target.appendChild($portalContainer);
  setPortalContainer($portalContainer);
};
