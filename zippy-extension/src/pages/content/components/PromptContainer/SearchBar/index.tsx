import React, { ChangeEvent, Dispatch, SetStateAction } from 'react';
import t from '@src/chrome/i18n';
import { SearchIcon } from '@pages/content/components/Icons';

interface SearchBarProps {
  searchTerm: string;
  setSearchTerm: Dispatch<SetStateAction<string>>;
  placeholder?: string;
}
const SearchBar = ({
  searchTerm,
  setSearchTerm,
  placeholder = t('searchInput_placeholder'),
}: SearchBarProps) => {
  const handleSearchChange = (e: ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  return (
    <div className="ZP_prompt-container__search-bar">
      <SearchIcon />
      <input value={searchTerm} placeholder={placeholder} onChange={handleSearchChange} />
    </div>
  );
};

export default SearchBar;
