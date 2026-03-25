import { HttpInterceptorFn, HttpErrorResponse } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const globalErrorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error('HTTP Global Error:', error);
      
      // We still re-throw the error so specific components can handle it,
      // but we've logged it and ensure the stream doesn't just hang.
      return throwError(() => error);
    })
  );
};
