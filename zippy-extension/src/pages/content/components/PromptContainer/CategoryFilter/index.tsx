import React, { Dispatch, SetStateAction } from 'react';
import { Category } from '@pages/content/types';
import { BookmarkCategoryIcon } from '@pages/content/components/Icons';

interface CategoryFilterProps {
  category: Array<Category>;
  selectedCategory: Category['value'];
  setSelectedCategory: Dispatch<SetStateAction<Category['value']>>;
  isBookmark: boolean;
  setIsBookmark: Dispatch<SetStateAction<boolean>>;
  userData: any;
}
const CategoryFilter = ({
  selectedCategory,
  setSelectedCategory,
  category,
  setIsBookmark,
  isBookmark,
  userData,
}: CategoryFilterProps) => {
  const handleCategoryClick = (
    e: React.MouseEvent<HTMLLIElement> | React.KeyboardEvent<HTMLLIElement>
  ) => {
    const { category } = e.currentTarget.dataset;
    setSelectedCategory(category as CategoryFilterProps['selectedCategory']);
    setIsBookmark(false);
  };

  const handleCategoryKeydown = (e: React.KeyboardEvent<HTMLLIElement>) => {
    if (e.key === 'Enter' || e.key === ' ') {
      e.preventDefault();
      handleCategoryClick(e);
    }
  };

  const handleBookmarkClick = () => {
    setIsBookmark(true);
    setSelectedCategory('BOOKMARK');
  };

  const handleBookmarkKeydown = (e: React.KeyboardEvent<HTMLLIElement>) => {
    if (e.key === 'Enter' || e.key === ' ') {
      e.preventDefault();
      handleBookmarkClick();
    }
  };

  return (
    <nav className="ZP_prompt-container__filter ZP_category">
      <ul>
        {category.map((categoryItem) => {
          return (
            <li
              key={categoryItem.id}
              onClick={handleCategoryClick}
              onKeyDown={handleCategoryKeydown}
              aria-label={categoryItem.text}
              data-category={categoryItem.value}
              className={!isBookmark && categoryItem.value === selectedCategory ? 'active' : ''}
            >
              {categoryItem.text}
            </li>
          );
        })}
        {userData && userData?.userUuid?.length > 0 && (
          <li
            onClick={handleBookmarkClick}
            onKeyDown={handleBookmarkKeydown}
            aria-label="bookmark"
            className={`bookmark ${isBookmark ? 'active' : ''}`}
          >
            <BookmarkCategoryIcon />
          </li>
        )}
      </ul>
    </nav>
  );
};

export default CategoryFilter;
