import { useCallback, useEffect, useState } from 'react';
import { createPromptPortal } from '@pages/content/utils/extension/prompt-portals/create-prompt-portal';
import { hideEmptyDiv } from '@pages/content/utils/extension/common/hide-empty-div';
import { shouldCreatePromptContainerPortal } from '@pages/content/utils/extension/prompt-portals/should-create-prompt-container-portal';
import { handleInitialChatPromptList } from '@pages/content/utils/extension/prompt-portals/handle-initial-chat-prompt-list';

const usePromptListPortal = () => {
  // 포탈 컨테이너를 저장하기 위한 state
  const [portalContainer, setPortalContainer] = useState(null);

  const createPromptPortalCallback = useCallback(() => createPromptPortal(setPortalContainer), []);

  const observeNextElement = useCallback(() => {
    // 변동사항을 처리하기 위한 핸들러
    const mutationHandler = (mutations) => {
      for (const mutation of mutations) {
        const $targetElement = mutation.target as HTMLElement;

        // 첫 화면에서 대화 시작 시 promptContainer 숨기기
        if (!document.querySelector('h1.text-4xl')) {
          handleInitialChatPromptList($targetElement);
        }

        // 프롬프트 컨테이너 생성이 필요한 경우 체크
        if (shouldCreatePromptContainerPortal($targetElement)) {
          createPromptPortalCallback();
          return;
        }

        // GPT 대화 화면의 불필요한 div 요소 제거
        hideEmptyDiv($targetElement);
      }
    };

    // 대상 요소의 변동사항을 관찰하는 MutationObserver 객체 생성
    const observer = new MutationObserver(mutationHandler);
    observer.observe(document.getElementById('__next'), { subtree: true, childList: true });

    return () => {
      observer.disconnect();
    };
  }, [createPromptPortalCallback]);

  useEffect(() => {
    return observeNextElement();
  }, [observeNextElement]);

  return portalContainer;
};

export default usePromptListPortal;
