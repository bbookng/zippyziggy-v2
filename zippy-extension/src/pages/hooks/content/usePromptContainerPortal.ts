import { ZP_PROMPT_CONTAINER_ID } from '@pages/constants';
import { useCallback, useEffect, useRef, useState } from 'react';
import { createPromptPortal } from '@pages/content/utils/extension/prompt-portals/create-prompt-portal';
import { hideEmptyDiv } from '@pages/content/utils/extension/common/hide-empty-div';
import { shouldCreatePromptContainerPortal } from '@pages/content/utils/extension/prompt-portals/should-create-prompt-container-portal';

const handleInitialChatPromptList = ($targetElement: HTMLElement) => {
  if ($targetElement.className === 'flex flex-col text-sm dark:bg-gray-800') {
    const $ZPPromptContainer = document.querySelector(`#${ZP_PROMPT_CONTAINER_ID}`) as HTMLElement;
    if ($ZPPromptContainer) $ZPPromptContainer.style.display = 'none';
  }
};

const usePromptListPortal = () => {
  const [portalContainer, setPortalContainer] = useState(null); // 포탈 컨테이너를 저장하는 state
  const isNewChatPage = useRef<boolean>(!window.location.href.includes('/c/')); // 현재 페이지가 새 채팅 페이지인지 여부를 저장하는 useRef

  const createPromptPortalCallback = useCallback(() => createPromptPortal(setPortalContainer), []);

  const observeNextElement = useCallback(
    (isNewChatPageRef: React.MutableRefObject<boolean>) => {
      const mutationHandler = (mutations) => {
        for (const mutation of mutations) {
          const $targetElement = mutation.target as HTMLElement;
          if (!document.querySelector('h1.text-4xl')) {
            handleInitialChatPromptList($targetElement);
          }
          if (shouldCreatePromptContainerPortal($targetElement, isNewChatPageRef)) {
            createPromptPortalCallback();
            return;
          }
          hideEmptyDiv($targetElement);
        }
      };
      const observer = new MutationObserver(mutationHandler);
      observer.observe(document.getElementById('__next'), { subtree: true, childList: true });

      return () => {
        observer.disconnect();
      };
    },
    [createPromptPortalCallback]
  );

  useEffect(() => {
    return observeNextElement(isNewChatPage);
  }, [observeNextElement, isNewChatPage]);

  return portalContainer;
};

export default usePromptListPortal;
