import { Directive, HostListener } from '@angular/core';

@Directive({
  selector: 'img',
})
export class ImgFallbackDirective {
  constructor() {}

  @HostListener('error', ['$event'])
  switchToFallBack(event: Event) {
    const imgTag = event.target as HTMLImageElement;
    imgTag.src =
      'https://www.ncenet.com/wp-content/uploads/2020/04/No-image-found.jpg';
  }
}
