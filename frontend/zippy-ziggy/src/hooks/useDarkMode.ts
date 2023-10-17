import { useEffect, useState } from 'react';

const useDarkMode = () => {
  // 현재 코드가 브라우저에서 실행되는지 확인
  const isBrowser = typeof window !== 'undefined';

  /**
   * 초기 테마 값을 반환하는 함수.
   * - 브라우저 환경이 아닌 경우 기본값은 'system'
   * - 로컬 스토리지에 저장된 테마 값이 있는 경우 해당 값을 반환
   * - 사용자의 시스템 설정에 따라 'dark' 또는 'light' 반환
   */
  const getInitialTheme = () => {
    if (!isBrowser) return 'system';

    const localTheme = window.localStorage.getItem('theme');
    return localTheme || 'system';
  };

  // 현재 테마 상태
  const [colorTheme, setTheme] = useState<string>(getInitialTheme);

  const setMode = (mode: string) => {
    if (mode === 'light' || mode === 'dark') {
      document.body.dataset.theme = mode;
      window.localStorage.setItem('theme', mode);
      setTheme(mode);
    } else {
      // 'system'을 처리합니다.
      const systemTheme = window.matchMedia('(prefers-color-scheme: dark)').matches
        ? 'dark'
        : 'light';
      document.body.dataset.theme = systemTheme;
      setTheme(systemTheme);
    }
  };

  const toggleTheme = () => {
    if (colorTheme === 'light' || colorTheme === 'system') {
      setMode('dark');
    } else {
      setMode('light');
    }
  };

  useEffect(() => {
    const localTheme = window.localStorage.getItem('theme');
    if (!localTheme || localTheme === 'system') {
      setMode(getInitialTheme());
    } else {
      setMode(localTheme);
    }
  }, []);

  return { colorTheme, toggleTheme };
};

export default useDarkMode;
