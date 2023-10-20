import { useCallback, useEffect, useState } from 'react';
import { ZP_INPUT_WRAPPER_ID, ZP_PROMPT_TITLE_HOLDER_ID } from '@pages/constants';
import { findRegenerateButton } from '@pages/content/utils/extension/common/find-regenerate-button';
import { hideEmptyDiv } from '@pages/content/utils/extension/common/hide-empty-div';
import { addToTopButton } from '@pages/content/utils/extension/input-portals/add-to-top-button';
import { adjustToBottomButtonPosition } from '@pages/content/utils/extension/input-portals/adjust-to-top-button-position';
import { shouldCreateInputWrapperPortal } from '@pages/content/utils/extension/input-portals/should-create-input-wrapper-portal';
import { addInputWrapperPortal } from '@pages/content/utils/extension/input-portals/add-input-wrapper-portal';
import { shouldAddToTopButton } from '@pages/content/utils/extension/input-portals/should-add-to-top-button';
import { appendShareButton } from '@pages/content/utils/extension/input-portals/append-share-button';

const useInputContainerPortal = () => {
  const [portalContainer, setPortalContainer] = useState<HTMLDivElement | null>(null);

  const memoizedAddInputWrapperPortal = useCallback(
    () => addInputWrapperPortal(setPortalContainer),
    []
  );

  useEffect(() => {
    // MutationObserver를 이용하여 __next 요소의 자식요소 추가, 제거, 변경을 감지하고,
    // 해당되는 경우 포탈을 생성하도록 addPromptContainerPortal 함수를 호출함
    const observer = new MutationObserver((mutations) => {
      for (const mutation of mutations) {
        const targetElement = mutation.target as Element;

        // 조건 상수 부분
        const IS_RESPONSE_WIDTH_CHANGE =
          targetElement.className === 'relative flex h-full flex-1 items-stretch md:flex-col';
        const SHOULD_SHOW_QUICK_FEATURES = targetElement.id === ZP_INPUT_WRAPPER_ID;
        const SHOULD_ADJUST_BOTTOM_BUTTON_POSITION = targetElement.className.includes(
          'react-scroll-to-bottom--css'
        );
        // -------------------------------------------------------------------------------------

        // client.width(반응형)가 변할 때 마다 공유 버튼 렌더링
        if (IS_RESPONSE_WIDTH_CHANGE) {
          appendShareButton();
        }

        // 맨 위로 가는 버튼이 생길 조건
        if (shouldAddToTopButton(targetElement)) {
          const scrollWrapper = document.querySelector("[class^='react-scroll-to-bottom--css']")
            ?.children[0];
          if (scrollWrapper.scrollHeight >= scrollWrapper.clientHeight) {
            addToTopButton(scrollWrapper);
          }
        }

        // 간편 기능이 보일 조건
        if (SHOULD_SHOW_QUICK_FEATURES) {
          const $ZPActionGroup = document.querySelector('#ZP_actionGroup');
          if (!$ZPActionGroup) return;
          const isNewChatPage = !window.location.href.includes('/c/');
          if (!isNewChatPage) $ZPActionGroup.classList.remove('ZP_invisible');
          if (findRegenerateButton()) $ZPActionGroup.classList.remove('ZP_invisible');
        }

        // GPT 사이트의 맨 아래로 가는 버튼의 위치를 조정
        if (SHOULD_ADJUST_BOTTOM_BUTTON_POSITION) {
          adjustToBottomButtonPosition(targetElement as HTMLElement);
        }

        // 포탈이 생길 조건
        if (shouldCreateInputWrapperPortal(targetElement)) {
          // 포탈 생성
          memoizedAddInputWrapperPortal();

          // 쉐어버튼 생성
          const $regenerateButton = findRegenerateButton();
          if ($regenerateButton) {
            const $ZPActionGroup = document.querySelector('#ZP_actionGroup');
            if ($ZPActionGroup) {
              $ZPActionGroup.classList.remove('ZP_invisible');
            }

            appendShareButton();

            document.getElementById(ZP_PROMPT_TITLE_HOLDER_ID).parentElement.style.display = 'none';
            const $textarea = document.querySelector(`form textarea`) as HTMLTextAreaElement;
            $textarea.placeholder = 'Send a message.';
          }
          return;
        }

        hideEmptyDiv(targetElement);
      }
    });

    // observer를 __next 요소에 observe하도록 설정
    const nextJSElement = document.getElementById('__next');
    if (nextJSElement) observer.observe(nextJSElement, { subtree: true, childList: true });

    // cleanup 함수에서 observer를 disconnect하도록 설정
    return () => {
      observer.disconnect();
    };
  }, [memoizedAddInputWrapperPortal]);

  return portalContainer;
};

export default useInputContainerPortal;
