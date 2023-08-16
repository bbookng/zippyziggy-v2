import { CHAT_GPT_URL, ZP_INPUT_WRAPPER_ID, ZP_PROMPT_TITLE_HOLDER_ID } from '@pages/constants';
import { removeFormParentClasses } from '@pages/content/utils/extension/input-portals/remove-form-parent-classes';
import { createPortalContainer } from '@pages/content/utils/extension/input-portals/create-portal-container';
import { addToggleButton } from '@pages/content/utils/extension/input-portals/add-toggle-button';
import { setInputWrapperStyle } from '@pages/content/utils/extension/input-portals/set-input-wrapper-style';

export const addInputWrapperPortal = (setPortalContainer) => {
  const existingPortal = document.getElementById(ZP_INPUT_WRAPPER_ID);
  if (existingPortal) return;

  const $formParent = document.querySelector('form:not(.signup__form)')?.parentElement;
  if (!$formParent) return;

  // 클래스 제거
  removeFormParentClasses($formParent);
  // 인풋 포탈을 생성
  const $inputWrapperPortal = createPortalContainer();
  if (!$inputWrapperPortal) return;

  // 토글 버튼을 생성 후 주입
  addToggleButton($formParent);
  // 입력창 focus 시 border 스타일 지정
  setInputWrapperStyle($inputWrapperPortal.parentElement);
  const $selectedPromptTitleWrapper = document.createElement('div');

  const $selectedPromptTitle = document.createElement('p');
  $selectedPromptTitle.id = ZP_PROMPT_TITLE_HOLDER_ID;

  $inputWrapperPortal.prepend($selectedPromptTitleWrapper);
  $selectedPromptTitleWrapper.appendChild($selectedPromptTitle);
  const message = { type: 'renderInputPortals' };
  window.postMessage(message, CHAT_GPT_URL);
  setPortalContainer($inputWrapperPortal);
};
