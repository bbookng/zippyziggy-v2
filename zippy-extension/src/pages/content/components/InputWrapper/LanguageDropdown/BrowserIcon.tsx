import React from 'react';
import {
  BraveIcon,
  ChromeIcon,
  EdgeIcon,
  SafariIcon,
  UnknownIcon,
  WhaleIcon,
} from '@pages/content/components/Icons';

interface BrowserIconProps {
  name: string;
}
const BrowserIcon = ({ name }: BrowserIconProps) => {
  const browseName = name.toLowerCase();
  switch (browseName) {
    case 'brave':
      return <BraveIcon />;
    case 'chrome':
      return <ChromeIcon />;
    case 'edge':
      return <EdgeIcon />;
    case 'internet explorer':
    case 'unknown':
      return <UnknownIcon />;
    case 'safari':
      return <SafariIcon />;
    case 'whale':
      return <WhaleIcon />;
    default:
      return null;
  }
};

export default BrowserIcon;
