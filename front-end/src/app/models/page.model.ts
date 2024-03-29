export class PageModel<T>{
  content!:T[];
  pageable!: {
      sort: {
        empty: boolean,
          sorted: boolean,
          unsorted: boolean
      };
      offset: number;
        pageSize: number;
        pageNumber: number;
        unpaged: boolean;
        paged: boolean
  };
  last!: boolean;
  totalElements!: number;
  totalPages!: number;
  size!: number;
  number!: number;
  sort!: {
  empty: boolean;
    sorted: boolean;
    unsorted: boolean
};
  numberOfElements!: number;
  first!: boolean;
  empty!: boolean;
}
