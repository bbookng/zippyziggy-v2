import { useEffect, useState } from 'react';

const useDarkMode = () => {
  const [colorTheme, setTheme] = useState<string | null>(null);

  const setMode = (mode: string) => {
    if (mode === 'light') {
      document.body.dataset.theme = 'light';
      window.localStorage.setItem('theme', 'light');
    } else {
      document.body.dataset.theme = 'dark';
      window.localStorage.setItem('theme', 'dark');
    }
    setTheme(mode);
  };

  const toggleTheme = () => {
    colorTheme === 'light' ? setMode('dark') : setMode('light');
  };

  // next는 기본적으로 SSG를 지원하기 때문에 렌더링이 일어나기전에 브라우저 환경에서 사용할 수 있는
  // localStorage, window 객체 등에 접근 불가
  // => useEffect를 통해 마운트 후 사용
  useEffect(() => {
    const localTheme = window.localStorage.getItem('theme');
    if (localTheme) {
      setMode(localTheme);
    } else if (window.matchMedia('(prefers-color-scheme:dark)').matches) {
      setMode('dark');
    } else {
      setMode('light');
    }
  }, []);

  return { colorTheme, toggleTheme };
};

export default useDarkMode;
