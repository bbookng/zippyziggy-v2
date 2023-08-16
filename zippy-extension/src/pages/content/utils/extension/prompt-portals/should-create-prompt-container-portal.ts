// 대상 요소가 포탈을 생성해야 하는지 판단하는 함수
export const shouldCreatePromptContainerPortal = (
  targetElement: Element,
  isNewChatPageRef: React.MutableRefObject<boolean>
): boolean => {
  const isNewChatPage = isNewChatPageRef.current;

  return (
    targetElement.className === 'flex flex-col text-sm dark:bg-gray-800 h-full' ||
    targetElement.className.includes('relative flex h-full max-w-full flex-1') ||
    (isNewChatPage && targetElement.className === 'overflow-hidden w-full h-full relative flex') ||
    (isNewChatPage &&
      targetElement.className ===
        'relative h-full w-full transition-width flex flex-col overflow-auto items-stretch flex-1') ||
    (isNewChatPage && targetElement.className.includes('react-scroll-to-bottom--css')) ||
    targetElement.id === '__next'
  );
};
