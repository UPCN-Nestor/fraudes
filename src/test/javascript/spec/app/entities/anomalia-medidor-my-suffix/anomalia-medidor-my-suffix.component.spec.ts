/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FrTestModule } from '../../../test.module';
import { AnomaliaMedidorMySuffixComponent } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.component';
import { AnomaliaMedidorMySuffixService } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.service';
import { AnomaliaMedidorMySuffix } from '../../../../../../main/webapp/app/entities/anomalia-medidor-my-suffix/anomalia-medidor-my-suffix.model';

describe('Component Tests', () => {

    describe('AnomaliaMedidorMySuffix Management Component', () => {
        let comp: AnomaliaMedidorMySuffixComponent;
        let fixture: ComponentFixture<AnomaliaMedidorMySuffixComponent>;
        let service: AnomaliaMedidorMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [AnomaliaMedidorMySuffixComponent],
                providers: [
                    AnomaliaMedidorMySuffixService
                ]
            })
            .overrideTemplate(AnomaliaMedidorMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AnomaliaMedidorMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AnomaliaMedidorMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AnomaliaMedidorMySuffix(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.anomaliaMedidors[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
