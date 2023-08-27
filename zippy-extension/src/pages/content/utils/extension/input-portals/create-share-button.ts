import { ZP_SHARE_BUTTON_ID } from '@pages/constants';
import t from '@src/chrome/i18n';
import { SHARE_DESKTOP_SVG, SHARE_MOBILE_SVG } from '@pages/content/components/Icons';

export const createShareButton = () => {
  if (document.getElementById(ZP_SHARE_BUTTON_ID)) return null;

  const $shareButton = document.createElement('button');
  $shareButton.id = ZP_SHARE_BUTTON_ID;
  $shareButton.classList.add(
    'btn',
    'relative',
    'btn-neutral',
    'whitespace-nowrap-z-0',
    'border-0',
    'md:border',
    'mr-1'
  );
  $shareButton.style.fontSize = '13px';

  const $shareButtonContent = document.createElement('div');
  $shareButtonContent.classList.add('flex', 'w-full', 'gap-2', 'items-center', 'justify-center');
  if (document.body.clientWidth >= 768) {
    $shareButtonContent.innerHTML = `
    ${SHARE_DESKTOP_SVG}
                            ${t('shareButton_default')} ${t('shareButton_ready')}`;
  } else {
    $shareButtonContent.innerHTML = SHARE_MOBILE_SVG;
  }
  $shareButton.appendChild($shareButtonContent);

  return $shareButton;
};
