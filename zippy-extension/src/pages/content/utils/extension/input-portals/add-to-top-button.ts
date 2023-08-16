import { ZP_TO_TOP_BUTTON_ID } from '@pages/constants';

export const addToTopButton = ($formParent) => {
  const $toTopButton = document.createElement('button');
  $toTopButton.id = ZP_TO_TOP_BUTTON_ID;

  if (document.getElementById(ZP_TO_TOP_BUTTON_ID)) return;

  $toTopButton.innerHTML = `
  <svg stroke="currentColor" fill="none" stroke-width="2" viewBox="0 0 24 24" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4 m-1" height="1em" width="1em" xmlns="http://www.w3.org/2000/svg" style="transform: scale(1, -1);">
  <line x1="12" y1="5" x2="12" y2="19"></line>
  <polyline points="19 12 12 19 5 12"></polyline>
</svg>
`;
  $toTopButton.classList.add(
    'cursor-pointer',
    'absolute',
    'right-6',
    'z-10',
    'rounded-full',
    'border',
    'border-gray-200',
    'bg-gray-50',
    'text-gray-600',
    'dark:border-white/10',
    'dark:bg-white/10',
    'dark:text-gray-200',
    'bottom-1',
    'right-[120px]'
  );
  $formParent.appendChild($toTopButton);

  const handleWindowToTopClick = () => {
    document.querySelector("[class^='react-scroll-to-bottom--css']").children[0].scrollTo({
      top: 0,
      left: 0,
      behavior: 'smooth',
    });
  };
  $toTopButton.addEventListener('click', handleWindowToTopClick);
};
