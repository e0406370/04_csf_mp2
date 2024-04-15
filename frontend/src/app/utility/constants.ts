/*
  Enforces the following password requirements:
    - At least one digit (?=.*[0-9])
    - At least one lowercase letter (?=.*[a-z])
    - At least one uppercase letter (?=.*[A-Z])
    - At least one special character or underscore (?=.*[\\W_])
    - No whitespace characters (?!.*\\s)
    - Minimum length of 10 characters .{10,}
*/
export const PASSWORD_REGEX: string =
  '^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])(?!.*\\s).{10,}$';

export const ERROR_MESSAGE: string =
  'Unknown error occurred. Please try again later.';

export const SELECTED_COUNTRIES: string[] = [
  'Australia',
  'Brunei',
  'Denmark',
  'Indonesia',
  'Japan',
  'Malaysia',
  'Myanmar',
  'New Zealand',
  'Singapore',
  'Switzerland',
  'South Korea',
  'Taiwan',
  'Thailand',
  'United Kingdom',
  'United Arab Emirates',
];

export const MAPS_API_KEY: string = 'AIzaSyBYk-vIXx1hrdCxyBNJ7ubJ7I6HvcSCVxQ';
