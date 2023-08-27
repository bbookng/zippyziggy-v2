import React from 'react';

interface Props {
  url: string;
  onClick: () => void;
  backgroundColor: string;
  svg: React.ReactNode;
}

export default function SocialLoginButton({ url, onClick, backgroundColor, svg }: Props) {
  return (
    <a href={url} style={{ backgroundColor }} onClick={onClick}>
      <span>{svg}</span>
    </a>
  );
}
