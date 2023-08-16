import { ZP_PROMPT_CONTAINER_ID } from '@pages/constants';

export const shouldCreateInputWrapperPortal = (element: Element): boolean => {
  const { id, className } = element;
  const isPromptContainer = id === ZP_PROMPT_CONTAINER_ID;
  const isRoot = id === '__next';
  const isInputWrapper = className?.includes(
    'h-full flex ml-1 md:w-full md:m-auto md:mb-4 gap-0 md:gap-2 justify-center'
  );
  const isChange = className.includes(
    'relative h-full w-full transition-width flex flex-col overflow-auto items-stretch flex-1'
  );

  return Boolean(isPromptContainer || isRoot || isInputWrapper || isChange);
};
