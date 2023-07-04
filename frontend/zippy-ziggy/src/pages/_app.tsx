import GlobalStyle from '@/styles/Global.style';
import useDarkMode from '@/hooks/useDarkMode';
import { media } from '@/styles/media';
import { darkTheme, lightTheme } from '@/styles/theme';
import type { AppProps } from 'next/app';
import { ThemeProvider, createGlobalStyle } from 'styled-components';
import normalize from 'styled-normalize';
import '@/styles/index.css';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import AppLayout from '@/layout/AppLayout';
import store, { persistor } from '@/core/store';
import { PersistGate } from 'redux-persist/integration/react';
import { Provider } from 'react-redux';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import 'toastify-js/src/toastify.css';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import DefaultHead from '@/components/Head/DefaultHead';
import Construction from './construction';

const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      refetchOnWindowFocus: false, // default: true
    },
  },
});

function App({ Component, pageProps }: AppProps) {
  const { colorTheme, toggleTheme } = useDarkMode();

  return (
    <Provider store={store}>
      <DefaultHead />
      <PersistGate persistor={persistor}>
        {/* loading={<div></div>}  */}
        <QueryClientProvider client={queryClient}>
          <ThemeProvider theme={colorTheme === 'dark' ? darkTheme : lightTheme}>
            <AppLayout toggleTheme={toggleTheme}>
              {/* <Construction /> */}
              <Component {...pageProps} />
              <ToastContainer
                limit={1}
                pauseOnFocusLoss={false}
                theme={colorTheme === 'dark' ? 'dark' : 'light'}
              />
            </AppLayout>
          </ThemeProvider>
          <ReactQueryDevtools initialIsOpen={false} />
        </QueryClientProvider>
      </PersistGate>
    </Provider>
  );
}

export default App;
