import { ZP_INPUT_SECTION_ID } from '@pages/constants';

export const removeFormParentClasses = (formParent) => {
  const $formParent = formParent;
  const classesToRemove = [
    'absolute',
    'md:!bg-transparent',
    'md:border-t-0',
    'md:dark:border-transparent',
    'md:border-transparent',
  ];

  $formParent.classList.remove(...classesToRemove);
  $formParent.classList.add('relative');
  $formParent.parentElement.classList.add('flex-col');
  $formParent.id = ZP_INPUT_SECTION_ID;
};
