import Footer from '@/components/Footer/Footer';
import Navbar from '@/components/Navbar/Navbar';
import GlobalStyle from '@/styles/Global.style';
import Head from 'next/head';
import React from 'react';

type AppLayoutProps = {
  children: React.ReactNode;
  toggleTheme: () => void;
};

function AppLayout({ children, toggleTheme }: AppLayoutProps) {
  return (
    <>
      <GlobalStyle />
      <Navbar toggleTheme={toggleTheme} />
      <div>{children}</div>
    </>
  );
}

export default AppLayout;
