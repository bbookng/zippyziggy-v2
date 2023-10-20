import { ZP_PROMPT_CONTAINER_ID } from '@pages/constants';
import { containsAllClasses } from '@src/utils';

export const shouldCreateInputWrapperPortal = (element: Element): boolean => {
  const { id, className } = element;
  const isPromptContainer = id === ZP_PROMPT_CONTAINER_ID;
  const isRoot = id === '__next';
  const isInputWrapper = containsAllClasses(
    className,
    'relative flex h-full flex-1 items-stretch md:flex-col'
  );
  const isChange = containsAllClasses(className, 'relative h-full w-full');

  return Boolean(isPromptContainer || isRoot || isInputWrapper || isChange);
};
