import { ZP_INPUT_WRAPPER_ID } from '@pages/constants';

export const createPortalContainer = () => {
  const $parent = document.querySelector('textarea')?.parentElement ?? null;
  if (!$parent) return null;

  const $inputWrapperPortal = document.createElement('div');
  $inputWrapperPortal.id = ZP_INPUT_WRAPPER_ID;
  $parent.prepend($inputWrapperPortal);

  return $inputWrapperPortal;
};
