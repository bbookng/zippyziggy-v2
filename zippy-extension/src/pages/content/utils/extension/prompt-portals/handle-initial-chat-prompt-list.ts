import { ZP_PROMPT_CONTAINER_ID } from '@pages/constants';

export const handleInitialChatPromptList = ($targetElement: HTMLElement) => {
  if ($targetElement.className === 'flex flex-col text-sm dark:bg-gray-800') {
    const $ZPPromptContainer = document.querySelector(`#${ZP_PROMPT_CONTAINER_ID}`) as HTMLElement;
    if ($ZPPromptContainer) $ZPPromptContainer.style.display = 'none';
  }
};
