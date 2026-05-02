import { Directive, HostListener, Input } from '@angular/core';

@Directive({
  selector: 'img[appImgFallback]',
})
export class ImgFallbackDirective {
  @Input('appImgFallback') fallbackUrl: string = '';

  constructor() {}

  @HostListener('error', ['$event'])
  switchToFallBack(event: Event) {
    const imgTag = event.target as HTMLImageElement;
    imgTag.src = this.fallbackUrl || 'https://www.ncenet.com/wp-content/uploads/2020/04/No-image-found.jpg';
  }
}
