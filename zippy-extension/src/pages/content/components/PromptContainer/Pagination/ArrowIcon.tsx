import React from 'react';
import {
  LeftArrowIcon,
  LeftDoubleArrowIcon,
  RightArrowIcon,
  RightDoubleArrowIcon,
} from '@pages/content/components/Icons';

interface ArrowIconProps {
  name: 'left' | 'right' | 'leftDouble' | 'rightDouble';
  size: string;
}
const ArrowIcon = ({ name, size }: ArrowIconProps) => {
  switch (name) {
    case 'left':
      return <LeftArrowIcon size={size} />;
    case 'right':
      return <RightArrowIcon size={size} />;
    case 'leftDouble':
      return <LeftDoubleArrowIcon size={size} />;
    case 'rightDouble':
      return <RightDoubleArrowIcon size={size} />;
    default:
      return null;
  }
};

export default ArrowIcon;
