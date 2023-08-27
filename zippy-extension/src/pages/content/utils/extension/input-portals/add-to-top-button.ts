import { ZP_TO_TOP_BUTTON_ID } from '@pages/constants';
import { TOP_ARROW } from '@pages/content/components/Icons';

export const addToTopButton = ($formParent) => {
  const $toTopButton = document.createElement('button');
  $toTopButton.id = ZP_TO_TOP_BUTTON_ID;

  if (document.getElementById(ZP_TO_TOP_BUTTON_ID)) return;

  $toTopButton.innerHTML = TOP_ARROW;
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
