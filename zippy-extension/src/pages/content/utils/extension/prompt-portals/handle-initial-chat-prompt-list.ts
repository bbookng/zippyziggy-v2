import { ZP_PROMPT_CONTAINER_ID } from '@pages/constants';
import { containsAllClasses } from '@src/utils';

export const handleInitialChatPromptList = ($targetElement: HTMLElement) => {
  if (containsAllClasses($targetElement.className, 'flex flex-col text-sm dark:bg-gray-800')) {
    const $ZPPromptContainer = document.querySelector(`#${ZP_PROMPT_CONTAINER_ID}`) as HTMLElement;
    if ($ZPPromptContainer) $ZPPromptContainer.style.display = 'none';
  }
};
