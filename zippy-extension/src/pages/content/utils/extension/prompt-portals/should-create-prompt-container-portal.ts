// 대상 요소가 포탈을 생성해야 하는지 판단하는 함수
import { containsAllClasses } from '@src/utils';

export const shouldCreatePromptContainerPortal = (targetElement: Element): boolean => {
  const isInitialEntryClass = 'flex flex-col text-sm dark:bg-gray-800 h-full';
  const isNewChatButtonClickedClass =
    'relative w-full flex-1 overflow-auto transition-width h-full';

  // 첫 번째 조건: 다른 곳에서 처음 진입했을 경우
  const isInitialEntry = containsAllClasses(targetElement.className, isInitialEntryClass);
  // 두 번째 조건: new chat 버튼을 클릭했을 경우
  const isNewChatButtonClicked = containsAllClasses(
    targetElement.className,
    isNewChatButtonClickedClass
  );
  // 세 번째 조건: Next.js root인 경우
  const isNextJsRoot = targetElement.id === '__next';

  return isInitialEntry || isNewChatButtonClicked || isNextJsRoot;
};
