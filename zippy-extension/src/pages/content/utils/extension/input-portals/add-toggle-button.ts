import { ZP_HIDE_TOGGLE_BUTTON_ID } from '@pages/constants';
import logo from '@assets/img/icon16.png';

export const addToggleButton = ($formParent) => {
  const $hideToggleButton = document.createElement('button');
  $hideToggleButton.id = ZP_HIDE_TOGGLE_BUTTON_ID;

  const $img = document.createElement('img');
  $img.src = logo;
  $hideToggleButton.appendChild($img);

  const $text = document.createTextNode('Zippy');
  $hideToggleButton.appendChild($text);
  $hideToggleButton.classList.add('bg-white', 'dark:bg-gray-800', 'dark:border-white/20');

  $formParent.appendChild($hideToggleButton);

  const handleInputSectionToggle = () => {
    $formParent.classList.toggle('hide');
  };
  $hideToggleButton.addEventListener('click', handleInputSectionToggle);
};
